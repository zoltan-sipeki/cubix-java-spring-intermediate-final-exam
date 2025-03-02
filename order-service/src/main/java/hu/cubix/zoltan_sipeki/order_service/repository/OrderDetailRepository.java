package hu.cubix.zoltan_sipeki.order_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.cubix.zoltan_sipeki.common_lib.constant.OrderStatus;
import hu.cubix.zoltan_sipeki.order_service.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @EntityGraph(attributePaths = { "items" })
    public List<OrderDetail> findByUsername(String username);
    
    @EntityGraph(attributePaths = { "items" })
    public Optional<OrderDetail> findByIdAndStatus(long id, OrderStatus status);

    @Modifying
    @Query("update OrderDetail o set o.status = :status where o.id = :id")
    public int updateStatus(long id, OrderStatus status);

    @Modifying
    @Query("update OrderDetail o set o.shipmentId = :shipmentId where o.id = :id")
    public int updateShipmentId(long id, long shipmentId);
}
