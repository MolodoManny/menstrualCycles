package savickiy.corp.entity.jpa;

import jakarta.persistence.*;
import lombok.*;
import savickiy.corp.entity.enums.DischargeColor;
import savickiy.corp.entity.enums.DischargeIntensity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "discharge",
        uniqueConstraints = @UniqueConstraint(name = "uq_discharge_per_day",
                columnNames = {"cycles_id", "date"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cycles_id", nullable = false)
    private MenstrualCycle cycle;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DischargeIntensity intensity;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DischargeColor color;

    @Column(name = "notes")
    private String notes;


}
