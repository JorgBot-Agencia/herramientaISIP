package com.formato.isp.GestionEmpresa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> educacion = new ArrayList<String>();
        educacion.add("India");
        educacion.add("Pakistan");
        educacion.add("Australia");
        educacion.add("England");
        educacion.add("South Africa");

        List<String> privado = new ArrayList<String>();
        privado.add("Brazil");
        privado.add("Spain");
        privado.add("Germany");
        privado.add("Netherlands");
        privado.add("Italy");

        List<String> publico = new ArrayList<String>();
        publico.add("United States");
        publico.add("Spain");
        publico.add("Argentina");
        publico.add("France");
        publico.add("Russia");

        List<String> ci = new ArrayList<String>();
        ci.add("United States");
        ci.add("Spain");
        ci.add("France");
        ci.add("Russia");

        expandableListDetail.put("CRICKET TEAMS", educacion);
        expandableListDetail.put("FOOTBALL TEAMS", privado);
        expandableListDetail.put("BASKETBALL TEAMS", publico);
        expandableListDetail.put("CI", ci);
        return expandableListDetail;
    }
}