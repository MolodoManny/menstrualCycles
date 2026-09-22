package savickiy.corp.entity.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_prediction_time", nullable = false)
    private LocalDateTime startPredictionTime;

    @Column(name = "end_prediction_time", nullable = false)
    private LocalDateTime endPredictionTime;

    @Column(name = "ovulation_prediction_time", nullable = false)
    private LocalDateTime ovulationPredictionTime;

    @Column(name = "fertile_prediction_start")
    private LocalDateTime fertilePredictionStart;

    @Column(name = "fertile_prediction_end")
    private LocalDateTime fertilePredictionEnd;

    @Column(name = "avg_cycle_length")
    private Integer avgCycleLength;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
