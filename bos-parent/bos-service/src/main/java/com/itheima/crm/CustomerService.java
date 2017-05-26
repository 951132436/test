
package com.itheima.crm;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CustomerService", targetNamespace = "http://crm.itheima.com/")
@XmlSeeAlso({
    //ObjectFactory.class
})
public interface CustomerService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findDecidedzoneIdByAddress", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindDecidedzoneIdByAddress")
    @ResponseWrapper(localName = "findDecidedzoneIdByAddressResponse", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindDecidedzoneIdByAddressResponse")
    public String findDecidedzoneIdByAddress(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "associationDecidedzon", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.AssociationDecidedzon")
    @ResponseWrapper(localName = "associationDecidedzonResponse", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.AssociationDecidedzonResponse")
    public void associationDecidedzon(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        List<String> arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns com.itheima.crm.Customer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findCustomerByTel", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindCustomerByTel")
    @ResponseWrapper(localName = "findCustomerByTelResponse", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindCustomerByTelResponse")
    public Customer findCustomerByTel(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<com.itheima.crm.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindAllResponse")
    public List<Customer> findAll();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<com.itheima.crm.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findListHasAssociation", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindListHasAssociation")
    @ResponseWrapper(localName = "findListHasAssociationResponse", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindListHasAssociationResponse")
    public List<Customer> findListHasAssociation(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<com.itheima.crm.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findListNotAssociation", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindListNotAssociation")
    @ResponseWrapper(localName = "findListNotAssociationResponse", targetNamespace = "http://crm.itheima.com/", className = "com.itheima.crm.FindListNotAssociationResponse")
    public List<Customer> findListNotAssociation();

}