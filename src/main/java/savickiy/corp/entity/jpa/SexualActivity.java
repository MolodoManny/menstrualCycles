package savickiy.corp.entity.jpa;

import jakarta.persistence.*;
import lombok.*;
import savickiy.corp.entity.enums.ProtectionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sexual_activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SexualActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "protected", nullable = false)
    private Boolean protectedSex;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_protected", nullable = false, length = 50)
    private ProtectionType protectionType;

    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
