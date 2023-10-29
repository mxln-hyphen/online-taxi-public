package com.mxln.innercommon.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author mxln
 * @since 2023-10-29
 */
@Data
@TableName("driver_car_binding_relationship")
public class DriverCarBindingRelationshipDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long driverId;

    private Long carId;

    private Integer bindState;

    private LocalDateTime bindingTime;

    private LocalDateTime unBindingTime;

    @Override
    public String toString() {
        return "DriverCarBindingRelationship{" +
            "id=" + id +
            ", driverId=" + driverId +
            ", carId=" + carId +
            ", bindState=" + bindState +
            ", bindingTime=" + bindingTime +
            ", unBindingTime=" + unBindingTime +
        "}";
    }
}
