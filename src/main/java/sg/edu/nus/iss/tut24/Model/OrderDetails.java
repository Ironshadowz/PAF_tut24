package sg.edu.nus.iss.tut24.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails 
{
    private Integer id;
    private String product;
    private Float unitPrice;
    private Float discount;
    private Integer quantity;
    private Integer orderId;
}
