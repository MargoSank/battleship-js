package lv.ctco.javaschool.game.entity;

import lombok.Data;
import lv.ctco.javaschool.auth.entity.domain.User;
import javax.persistence.*;

@Data
@Entity
@Table(name = "toptable")
public class TopTable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User winName;
    private int shots;
}
