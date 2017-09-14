import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import java.io.*;
import java.util.Date;

public class Main {

    /**
     * Spel Injection Demonstration by Pieter De Cremer (Secure Code Warrior)
     *
     * Exploit input without escaping present:
     *          T(Runtime).getRuntime().exec("touch dangerous")
     * Exploit input that works even when escaping:
     *          T(Main).dangerous()
     *
     * A more contextual example: https://bitquark.co.uk/blog/2014/08/31/popping_a_shell_on_the_oculus_developer_portal
     */
    public static void main(String[] args) throws Exception {


        System.out.println("Enter a String evaluate:");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String line = stdin.readLine();

        //Use one of these two:
        Date date = evaluate(line);
        //Date date = escapeXmlAndEvaluate(line);

        System.out.println(date.toString());
    }

    public static Date evaluate(String line){
        line = org.apache.commons.lang.StringEscapeUtils.escapeXml(line);
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(line);
        Date date = (Date)exp.getValue();
        return date;
    }

    public static Date escapeXmlAndEvaluate(String line){
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(line);
        Date date = (Date)exp.getValue();
        return date;
    }

    public static void dangerous(){
        System.out.println("This is dangerous!");
    }
}

