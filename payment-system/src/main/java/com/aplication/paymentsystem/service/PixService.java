package com.aplication.paymentsystem.service;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.aplication.paymentsystem.domain.DTO.PixChargeDTO;
import com.aplication.paymentsystem.pix.Credentials;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class PixService {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    public JSONObject pixCreateEVP(){


        JSONObject options = jsonObjectConfig();

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

        JSONObject options = jsonObjectConfig();

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
            JSONObject response = efiPay.call("pixCreateImmediateCharge",
                    new HashMap<String, String>(), new JSONObject());

            int idFromJson= response.getJSONObject("loc").getInt("id");
            pixGenerateQRCode(String.valueOf(idFromJson));

            return response;

        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    private void pixGenerateQRCode(String id){
        JSONObject options = jsonObjectConfig();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        try {
            EfiPay efi= new EfiPay(options);
            Map<String, Object> response = efi.call("pixGenerateQRCode", params, new HashMap<String, Object>());

            System.out.println(response);

            File outputfile = new File("qrCodeImage.png");
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(javax.xml.bind.DatatypeConverter.parseBase64Binary(((String) response.get("imagemQrcode")).split(",")[1]))), "png", outputfile);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(outputfile);

        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private JSONObject jsonObjectConfig(){

        Credentials credentials = new Credentials();

        JSONObject options = new JSONObject();

        options.put("client_id", clientId);
        options.put("client_secret", clientSecret);
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        return options;
    }
}