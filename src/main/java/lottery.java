import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Random;

public class lottery {

    public static boolean outlier(int maxTotalIguales){

        return  maxTotalIguales < 19 || maxTotalIguales > 36;
    }

    public static void looping(String pathNum,int numeros) throws IOException
    {

        FileWriter fichero = new FileWriter(pathNum);
        PrintWriter pw = new PrintWriter(fichero);
        Random r = new Random();
        int cnt=0;

        for(int i=1; i <= numeros; i++) {
            int segundos = new Date().getSeconds();
            int valorAcumulado = 0;
            int valorActual ;
            int valorAnterior = 0;
            int iguales = 0;
            int maxTotalIguales = 0;
            cnt = 0;

            while(segundos == new Date().getSeconds()) {
                boolean b = r.nextBoolean();
                valorActual = (b  ? 1 : -1);
                valorAcumulado = valorAcumulado + valorActual;
                if(valorAnterior == valorActual)iguales++;
                else {
                    valorAnterior = valorActual;
                    if(iguales > maxTotalIguales)maxTotalIguales = iguales;
                    iguales = 0;
                }
                cnt++;
            }


            if(outlier(maxTotalIguales)){
                System.out.println("* \t"+i+"\t"+valorAcumulado+"\t"+ maxTotalIguales +" - "+cnt+" - "+(float)maxTotalIguales/cnt);
                pw.println(i+"\t"+valorAcumulado+"\t"+"*");
            }else{
                pw.println(i+"\t"+valorAcumulado);
                System.out.println("\t"+i+"\t"+valorAcumulado+"\t"+ maxTotalIguales +" - "+cnt+" - "+(float)maxTotalIguales/cnt);
            }
        }
        fichero.close();
    }


    public static void main(String[] args) {

        String seleccion = "Euromillones";
        String pathNum = "Euro_Num_";
        String pathEstrellas = "Euro_Estrellas_";
        int numNumeros = 50;
        int numEstrellas = 12;

        if(args.length > 0) {
            pathNum = "Once_Num_";
            pathEstrellas = "Once_Soles_";
            seleccion = "Eurojackpot";
            numEstrellas = 10;
            numNumeros = 50;
        }


        LocalDate ld = LocalDate.now().plusDays(-1);
        ld = ld.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        pathNum = pathNum+ld+".txt";
        pathEstrellas = pathEstrellas+ld+".txt";


        try {
            System.out.println("\n\t----------------Imprimiendo Numeros de "+seleccion+ "----------------\n");
            looping(pathNum,numNumeros);

            System.out.println("\n\t----------------Imprimiendo Estrellas de "+seleccion+ "-------------------\n");
            looping(pathEstrellas,numEstrellas);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}