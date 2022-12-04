package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.entities.Customer;
import com.facturacion.ideas.api.enums.TypeCustomerEnum;
import com.facturacion.ideas.api.enums.TypeIdentificationEnum;

public class AdminPerson {

    public static void preUpdate(Customer customerCurrent,
                                 CustomerNewDTO customerUpdateDTO) {

        TypeIdentificationEnum typeIdentificationEnum = TypeIdentificationEnum.getTipoCompradorEnum(customerUpdateDTO.getTypeIdentification());

        if (typeIdentificationEnum != null) {
            customerCurrent.setTipoIdentificacion(typeIdentificationEnum);
        }


        customerCurrent.setNumeroIdentificacion(
                customerUpdateDTO.getNumberIdentification() == null ?
                        customerCurrent.getNumeroIdentificacion() :
                        customerUpdateDTO.getNumberIdentification()
        );

        customerCurrent.setRazonSocial(
                customerUpdateDTO.getSocialReason() == null ?
                        customerCurrent.getRazonSocial() :
                        customerUpdateDTO.getSocialReason()
        );

        customerCurrent.setAddress(
                customerUpdateDTO.getAddress() == null ?
                        customerCurrent.getAddress() :
                        customerUpdateDTO.getAddress()
        );

        customerCurrent.setEmail(
                customerUpdateDTO.getEmail() == null ?
                        customerCurrent.getEmail() :
                        customerUpdateDTO.getEmail()
        );

        customerCurrent.setCellPhone(
                customerUpdateDTO.getCellPhone() == null ?
                        customerCurrent.getCellPhone() :
                        customerUpdateDTO.getCellPhone()
        );

        TypeCustomerEnum typeCustomerEnum = TypeCustomerEnum.getTypeCustomerEnum(customerUpdateDTO.getTypeCustomer());
        if (typeCustomerEnum != null)
            customerCurrent.setTypeCustomer(typeCustomerEnum);

        customerCurrent.setTlfConvencional(customerUpdateDTO.getTlfConvencional());


    }
}
