package sg.edu.nus.iss.tut24.Model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order 
{
    private Long orderId;
    private Date orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private Float tax;
}
