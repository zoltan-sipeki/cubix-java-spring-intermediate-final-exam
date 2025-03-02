
package hu.cubix.zoltan_sipeki.order_service.soap;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hu.cubix.zoltan_sipeki.order_service.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ShipOrder_QNAME = new QName("http://service.shipping_service.zoltan_sipeki.cubix.hu/", "ShipOrder");
    private final static QName _ShipOrderResponse_QNAME = new QName("http://service.shipping_service.zoltan_sipeki.cubix.hu/", "ShipOrderResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hu.cubix.zoltan_sipeki.order_service.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShipOrder }
     * 
     */
    public ShipOrder createShipOrder() {
        return new ShipOrder();
    }

    /**
     * Create an instance of {@link ShipOrderResponse }
     * 
     */
    public ShipOrderResponse createShipOrderResponse() {
        return new ShipOrderResponse();
    }

    /**
     * Create an instance of {@link ShippingDetailsSOAPDto }
     * 
     */
    public ShippingDetailsSOAPDto createShippingDetailsSOAPDto() {
        return new ShippingDetailsSOAPDto();
    }

    /**
     * Create an instance of {@link ShippingItemSOAPDto }
     * 
     */
    public ShippingItemSOAPDto createShippingItemSOAPDto() {
        return new ShippingItemSOAPDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShipOrder }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ShipOrder }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.shipping_service.zoltan_sipeki.cubix.hu/", name = "ShipOrder")
    public JAXBElement<ShipOrder> createShipOrder(ShipOrder value) {
        return new JAXBElement<ShipOrder>(_ShipOrder_QNAME, ShipOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShipOrderResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ShipOrderResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.shipping_service.zoltan_sipeki.cubix.hu/", name = "ShipOrderResponse")
    public JAXBElement<ShipOrderResponse> createShipOrderResponse(ShipOrderResponse value) {
        return new JAXBElement<ShipOrderResponse>(_ShipOrderResponse_QNAME, ShipOrderResponse.class, null, value);
    }

}
