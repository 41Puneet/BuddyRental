package com.buddyrental;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import java.util.LinkedHashMap;
import java.util.Map;

import com.buddyrental.Auth.LoginRequest;
import com.buddyrental.DTO.BookingDTO;
import com.buddyrental.DTO.PaymentDTO;
import com.buddyrental.DTO.UserRegisterDTO;
import com.buddyrental.DTO.VehicleDTO;
import com.buddyrental.Entity.User;
import com.buddyrental.Repository.User.UserRepository;
import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.GatewayName;
import com.buddyrental.enums.PaymentMethod;
import com.buddyrental.enums.PaymentStatus;
import com.buddyrental.enums.Role;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class ControllerFlowIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void customerCanCreateVehicleBookingAndPayment() throws Exception {
        String email = "customer-" + UUID.randomUUID() + "@example.com";
        String password = "secret123";

        JsonNode signUpResponse = postJson("/api/users/sign-up", registerRequest(email, password), 200);
        UUID customerId = UUID.fromString(signUpResponse.path("id").asText());
        String customerToken = loginAndGetToken(email, password);

        Map<String, Object> vehicleBody = new LinkedHashMap<>();
        vehicleBody.put("ownerId", customerId.toString());
        vehicleBody.put("type", VehicleType.BIKE.name());
        vehicleBody.put("brand", "Honda");
        vehicleBody.put("vehicleNumber", "BR-01-AB-" + UUID.randomUUID().toString().substring(0, 4));
        vehicleBody.put("description", "Test bike");
        vehicleBody.put("pricePerDay", 500);
        vehicleBody.put("securityDeposit", 2000);
        vehicleBody.put("fueltype", Fueltype.PETROL.name());
        vehicleBody.put("transmissionType", TransmissionType.MANUAL.name());
        vehicleBody.put("city", "Patna");
        vehicleBody.put("state", "Bihar");
        vehicleBody.put("available", true);

        JsonNode vehicleResponse = postJson("/api/vehicles/addVehicle", vehicleBody, 200, customerToken);
        assertThat(vehicleResponse.path("vehicleId").asText()).isNotEmpty();

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUserId(customerId);
        bookingDTO.setTotalPrice(1500);
        bookingDTO.setadvancePayment(500);

        JsonNode bookingResponse = postJson("/api/bookings/createBooking", bookingDTO, 200, customerToken);
        UUID bookingId = UUID.fromString(bookingResponse.path("bookingId").asText());
        assertThat(bookingResponse.path("bookingStatus").asText()).isEqualTo("Pending");
        assertThat(bookingResponse.path("paymentStatus").asText()).isEqualTo("Pending");

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setBookingId(bookingId);
        paymentDTO.setTransactionId("txn-" + UUID.randomUUID());
        paymentDTO.setAmount(500);
        paymentDTO.setPaymentMethod(PaymentMethod.UPI);
        paymentDTO.setPaymentStatus(PaymentStatus.Paid);
        paymentDTO.setGatewayName(GatewayName.RAZORPAY);

        JsonNode paymentResponse = postJson("/api/payments/create", paymentDTO, 200, customerToken);
        assertThat(paymentResponse.path("id").asText()).isNotEmpty();
        assertThat(paymentResponse.path("bookingId").asText()).isEqualTo(bookingId.toString());
    }

    @Test
    void adminOnlyEndpointRejectsCustomerAndAllowsAdmin() throws Exception {
        String customerEmail = "customer-admin-check-" + UUID.randomUUID() + "@example.com";
        String adminEmail = "admin-" + UUID.randomUUID() + "@example.com";
        String password = "secret123";

        postJson("/api/users/sign-up", registerRequest(customerEmail, password), 200);
        String customerToken = loginAndGetToken(customerEmail, password);

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail(adminEmail);
        admin.setPhoneNumber("9000000000");
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        String adminToken = loginAndGetToken(adminEmail, password);

        mockMvc.perform(get("/api/users/allUser")
                        .header("Authorization", bearer(customerToken)))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/users/allUser")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk());
    }

    private JsonNode postJson(String path, Object body, int expectedStatus) throws Exception {
        return postJson(path, body, expectedStatus, null);
    }

    private JsonNode postJson(String path, Object body, int expectedStatus, String bearerToken) throws Exception {
        var request = post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));
        if (bearerToken != null) {
            request.header("Authorization", bearer(bearerToken));
        }
        String response = mockMvc.perform(request)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString();
        return response.isBlank() ? objectMapper.createObjectNode() : objectMapper.readTree(response);
    }

    private String loginAndGetToken(String email, String password) throws Exception {
        JsonNode response = postJson("/api/users/login", loginRequest(email, password), 200);
        return response.path("token").asText();
    }

    private UserRegisterDTO registerRequest(String email, String password) {
        UserRegisterDTO request = new UserRegisterDTO();
        request.setFullname("Test User");
        request.setEmail(email);
        request.setPhoneNumber("9" + UUID.randomUUID().toString().substring(0, 9));
        request.setPassword(password);
        request.setConfirmPassword(password);
        return request;
    }

    private LoginRequest loginRequest(String email, String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
