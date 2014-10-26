package by.gravity.client.constans;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class Api {

    public static final String BASE_URL = "http://localhost:8080/main";

    public static final String METHOD = "method";

    public static final String METHOD_CREATE_USER = "createUser";
    public static final String METHOD_LOGIN_USER = "loginUser";
    public static final String METHOD_ADD_MONEY = "addMoney";
    public static final String METHOD_EXCHANGE = "exchange";

    public static final String USER_NAME = "userName";
    public static final String CURRENCY = "currency";
    public static final String SUM = "sum";
    public static final String FROM = "from";
    public static final String TO = "to";


    public static String createUser(String userName) {

        HashMap<String, String> params = new HashMap<>();
        params.put(METHOD, METHOD_CREATE_USER);
        params.put(USER_NAME, userName);
        return expandQuery(params);
    }

    public static String login(String userName){
        HashMap<String, String> params = new HashMap<>();
        params.put(METHOD, METHOD_LOGIN_USER);
        params.put(USER_NAME, userName);
        return expandQuery(params);
    }

    public static String addMoney(String userName,String currency,String sum){
        HashMap<String, String> params = new HashMap<>();
        params.put(METHOD, METHOD_ADD_MONEY);
        params.put(USER_NAME, userName);
        params.put(CURRENCY,currency);
        params.put(SUM,sum);
        return expandQuery(params);
    }

    public static String exchange(String userName,String from,String to,String sum){
        HashMap<String, String> params = new HashMap<>();
        params.put(USER_NAME,userName);
        params.put(METHOD, METHOD_EXCHANGE);
        params.put(FROM,from);
        params.put(TO,to);
        params.put(SUM,sum);
        return expandQuery(params);
    }

    private static String expandQuery(HashMap<String, String> params) {

        StringBuilder queryBuilder = new StringBuilder(BASE_URL);
        Set<String> keys = params.keySet();
        boolean isFirstParam = true;
        for (String key : keys) {
            if (isFirstParam) {
                isFirstParam = false;
                queryBuilder.append("?");
            } else {
                queryBuilder.append("&");
            }
            queryBuilder.append(key + "=" + params.get(key));
        }
        return queryBuilder.toString();
    }

}
