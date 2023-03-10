
package com.facturacion.ideas.api.sri.ws.recepcion;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "RecepcionComprobantesOfflineService", targetNamespace = "http://ec.gob.sri.ws.recepcion", wsdlLocation = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl")
public class RecepcionComprobantesOfflineService
    extends Service
{

    private final static URL RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
    private final static WebServiceException RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
    private final static QName RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME = new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION = url;
        RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION = e;
    }

    public RecepcionComprobantesOfflineService() {
        super(__getWsdlLocation(), RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME);
    }

    public RecepcionComprobantesOfflineService(WebServiceFeature... features) {
        super(__getWsdlLocation(), RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME, features);
    }

    public RecepcionComprobantesOfflineService(URL wsdlLocation) {
        super(wsdlLocation, RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME);
    }

    public RecepcionComprobantesOfflineService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME, features);
    }

    public RecepcionComprobantesOfflineService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RecepcionComprobantesOfflineService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns RecepcionComprobantesOffline
     */
    @WebEndpoint(name = "RecepcionComprobantesOfflinePort")
    public RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort() {
        return super.getPort(new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflinePort"), RecepcionComprobantesOffline.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RecepcionComprobantesOffline
     */
    @WebEndpoint(name = "RecepcionComprobantesOfflinePort")
    public RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflinePort"), RecepcionComprobantesOffline.class, features);
    }

    private static URL __getWsdlLocation() {
        if (RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION!= null) {
            throw RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
        }
        return RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
    }

}
