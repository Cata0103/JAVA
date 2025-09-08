//https://codeforces.com/problemset/problem/2081/F
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.PrintWriter;
public class FHotMatrix {
   static ArrayList<ArrayList<Integer>> miFuncionF(int numEntrada, ArrayList<ArrayList<Integer>> ll){
            if(numEntrada>1&&numEntrada%2==0){
                if(numEntrada==2){
                    ArrayList<Integer> al = new ArrayList<Integer>();
                    for(int i=0;i<numEntrada;i++){
                        al.add(i);
                    }
                    ll.add(al);
                    al = new ArrayList<Integer>();
                    for(int i=numEntrada-1;i>=0;i--){
                        al.add(i);
                    }
                    ll.add(al);
                }
                else{
                    ll = miFuncionF(numEntrada-2,ll);
                    ArrayList<Integer> al2 = new ArrayList<Integer>();
                    for(int i=0;i<numEntrada/2-1;i++){
                        al2.add(ll.get(numEntrada/2-1).get(i));
                    }
                    for(int j=0;j<numEntrada;j++){
                        if(al2.contains(numEntrada-1-j)==false&&al2.contains(j)==false){
                            ll.get(numEntrada/2-1).set(numEntrada/2-1,numEntrada-1-j);
                            break;
                        }
                    }
                    for(int i=0;i<numEntrada/2;i++){
                        int k = numEntrada/2;
                            ll.get(i).add(numEntrada-ll.get(i).get(numEntrada-k-1)-1);
                    }
                    int i = numEntrada/2;
                    al2 = new ArrayList<Integer>();
                    for(int k=0;k<=numEntrada/2;k++){
                        al2.add(numEntrada-1-ll.get(numEntrada-1-i).get(k));
                    }
                    ll.add(al2);
                    
                }
            }
            return ll;
    }
    static void imprimir(int numEntrada, ArrayList<ArrayList<Integer>> ll){
        PrintWriter out =new PrintWriter(System.out);
        if(numEntrada==1){
            out.print("YES"+"\n");
            out.print(0+"\n");
        }
        else if(numEntrada%2!=0){
            out.print("NO"+"\n");
        }else{
            out.print("YES"+"\n");
            for(int i=0;i<numEntrada;i++){
                if(i<numEntrada/2){
                    for(int k=0;k<numEntrada;k++){
                        if(k<numEntrada/2){
                            out.print(ll.get(i).get(k)+" ");
                        }else{
                            if(k==numEntrada-1){
                            out.print(numEntrada-1-ll.get(i).get(numEntrada-k-1)+"\n");
                            }else{
                            out.print(numEntrada-1-ll.get(i).get(numEntrada-k-1)+" ");
                            }
                        }
                }
                }else{
                    for(int k=0;k<numEntrada;k++){
                        if(k<numEntrada/2){
                            out.print(numEntrada-1-ll.get(numEntrada-1-i).get(k)+" ");
                        }else{
                            if(k==numEntrada-1){
                            out.print(ll.get(numEntrada-1-i).get(numEntrada-1-k)+"\n");
                            }else{
                                out.print(ll.get(numEntrada-1-i).get(numEntrada-1-k)+" ");
                            }
                        }
                    }
                }
            }
        }
        out.flush();
    }
    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        // BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        FileInputStream file = new FileInputStream("inFHotMatrix.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(file));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int numCasos = Integer.parseInt(st.nextToken());
        for(int i=0;i<numCasos;i++){
            st = new StringTokenizer(in.readLine());
            int numEntrada = Integer.parseInt(st.nextToken());
           ArrayList<ArrayList<Integer>> ll = new ArrayList<ArrayList<Integer>>();
            ll = miFuncionF(numEntrada,ll);
            imprimir(numEntrada, ll);
        }
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed/1000000);
        in.close();
    }
}