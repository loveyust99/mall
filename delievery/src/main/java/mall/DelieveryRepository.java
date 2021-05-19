package mall;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface DelieveryRepository extends PagingAndSortingRepository<Delievery, Long>{

    public Delievery findByOrderId(Long orderId);
}