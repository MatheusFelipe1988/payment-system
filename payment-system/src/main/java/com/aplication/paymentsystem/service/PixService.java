package com.aplication.paymentsystem.service;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.aplication.paymentsystem.domain.DTO.PixChargeDTO;
import com.aplication.paymentsystem.pix.Credentials;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PixService {

    public JSONObject pixCreateEVP(){

        Credentials credentials = new Credentials();

        JSONObject options = jsonObjectConfig(credentials);

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateEvp", new HashMap<String,String>(), new JSONObject());
            System.out.println(response.toString());
            return response;
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public JSONObject pixChargeCreate(PixChargeDTO pixChargeDTO){

        Credentials credentials = new Credentials();

        JSONObject options = jsonObjectConfig(credentials);

        JSONObject body = new JSONObject();

        body.put("calendario", new JSONObject().put("expiraçao", 3600));

        body.put("devedor", new JSONObject().put("cpf", "12345678909")
                .put("nome", "Juan Manuel Fangio"));

        body.put("valor", new JSONObject().put("original", pixChargeDTO.valor()));

        body.put("chave", pixChargeDTO.chave());


        JSONArray infoAdicionais = new JSONArray();

        infoAdicionais.put(new JSONObject().put("nome", "Campo 1")
                .put("valor", "Informação adicionao do PSP-recebedor"));

        infoAdicionais.put(new JSONObject().put("nome", "Campo 2")
                .put("valor", "Informação Adicional2 do PSP-Recebedor"));

        body.put("Informações Adicionais",infoAdicionais);

        try {
            EfiPay efiPay = new EfiPay(options);
            JSONObject response = efiPay.call("pixCreateImmedCharge",
                    new HashMap<String, String>(), new JSONObject());

            System.out.println(response);

        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    private JSONObject jsonObjectConfig(Credentials credentials){

        JSONObject options = new JSONObject();

        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        return options;
    }
}