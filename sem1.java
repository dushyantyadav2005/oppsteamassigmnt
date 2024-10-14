//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//
//public class sem1 implements sem {
//    private ArrayList<course>courses;
//    private int total_courses;
//    public sem1()
//    {
//        Scanner sc=new Scanner(System.in);
//        System.out.println("enter no of couses : ");
//        this.total_courses=sc.nextInt();
//        for(int i=0;i<total_courses;i++)
//        {
//            System.out.println("course code : ");
//            int cc=sc.nextInt();
//            System.out.println("course title : ");
//            String cn=sc.nextLine();
//            System.out.println("proffessor name : ");
//            String pn=sc.nextLine();
//            System.out.println("course credits : ");
//            int cc1=sc.nextInt();
//            System.out.println("enter no of prequisites : ");
//            int np=sc.nextInt();
//            ArrayList<Integer>ar=new ArrayList<>();
//            for(int k=0;k<np;k++)
//            {
//                System.out.println("enter prerequesite id : ");
//                Integer p=sc.nextInt();
//                ar.add(p);
//            }
//            System.out.println("enter timing : ");
//            String h=sc.nextLine();
//            course c=new course(cc,cn,pn,cc1,ar,h);
//            this.courses.add(c);
//        }
//    }
//    public void courseadd()
//    {
//        Scanner sc=new Scanner(System.in);
//        System.out.println("course code : ");
//        int cc=sc.nextInt();
//        System.out.println("course title : ");
//        String cn=sc.nextLine();
//        System.out.println("proffessor name : ");
//        String pn=sc.nextLine();
//        System.out.println("course credits : ");
//        int cc1=sc.nextInt();
//        System.out.println("enter no of prequisites : ");
//        int np=sc.nextInt();
//        ArrayList<Integer>ar=new ArrayList<>();
//        for(int k=0;k<np;k++)
//        {
//            System.out.println("enter prerequesite id : ");
//            Integer p=sc.nextInt();
//            ar.add(p);
//        }
//        System.out.println("enter timing : ");
//        String h=sc.nextLine();
//        course c=new course(cc,cn,pn,cc1,ar,h);
//        this.courses.add(c);
//    }
//    public void coursedisplay()
//    {
//        for(int i=0;i<total_courses;i++)
//        {
//            System.out.println(courses.get(i).toString());
//        }
//    }
//}
