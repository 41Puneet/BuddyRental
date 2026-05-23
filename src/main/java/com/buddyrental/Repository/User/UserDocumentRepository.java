package com.buddyrental.Repository.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.buddyrental.Entity.UserDocument;
import java.util.Optional;
import java.util.List;
import com.buddyrental.enums.DocumentType;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
    Optional<UserDocument> findByUserIdAndDocumentType(UUID userId, DocumentType documentType);
    List<UserDocument> findByUserId(UUID userId);
}
