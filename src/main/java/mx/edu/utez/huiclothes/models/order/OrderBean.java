package mx.edu.utez.huiclothes.models.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.models.log.LogBean;
import mx.edu.utez.huiclothes.models.stockControl.StockControlBean;
import mx.edu.utez.huiclothes.models.user.UserBean;

import java.time.LocalDate;
import java.util.*;
@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class OrderBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include

    private Long id;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @Column (nullable = false)
    private double total;
    @Column(length = 100, nullable = false)
    private String status;

    @ManyToMany(mappedBy = "orderBeans", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StockControlBean> stockControlBeans;

    @JsonIgnore
    @OneToMany(mappedBy = "orderBean", cascade = CascadeType.ALL)
    private Set<LogBean> logBeans;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserBean userBean;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressBean addressBean;

}
