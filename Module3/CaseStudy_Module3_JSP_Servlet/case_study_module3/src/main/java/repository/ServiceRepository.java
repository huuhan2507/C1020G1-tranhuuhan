package repository;

import bean.services.Service;

import java.util.List;

public interface ServiceRepository {
    List<Service> showService();

    void createService(Service service);

    void editService(Service service);

    void deleteService(Integer id);

    Service findServiceById(Integer id);

    List<Service> findServiceByName(String search);

}