package com.buddyrental.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;

import com.buddyrental.enums.DocumentType;


@Entity
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(nullable = false)
    private String documentNumber;

    @Column(nullable = false)
    private String documentUrl;

    private Boolean isVerified = false;

    private LocalDateTime uploadedAt;

    private LocalDateTime verifiedAt;

    private String rejectionReason;
}

