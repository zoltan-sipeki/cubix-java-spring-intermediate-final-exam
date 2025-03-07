
package hu.cubix.zoltan_sipeki.order_service.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 3.0.0
 * Generated source version: 3.0
 * 
 */
@WebService(name = "IShippingSOAPService", targetNamespace = "http://service.shipping_service.zoltan_sipeki.cubix.hu/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IShippingSOAPService {


    /**
     * 
     * @param arg0
     * @return
     *     returns long
     */
    @WebMethod(operationName = "ShipOrder")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "ShipOrder", targetNamespace = "http://service.shipping_service.zoltan_sipeki.cubix.hu/", className = "hu.cubix.zoltan_sipeki.order_service.soap.ShipOrder")
    @ResponseWrapper(localName = "ShipOrderResponse", targetNamespace = "http://service.shipping_service.zoltan_sipeki.cubix.hu/", className = "hu.cubix.zoltan_sipeki.order_service.soap.ShipOrderResponse")
    public long shipOrder(
        @WebParam(name = "arg0", targetNamespace = "")
        ShippingDetailsSOAPDto arg0);

}
