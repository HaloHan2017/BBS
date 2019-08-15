package bbs_class;

import java.util.Scanner;

public class Test {
//	public static void main(String[] args) {
////		TopicDao tdao=new TopicDao();
//////		TblLevel levelInfoByUid = tdao.getLevelInfoByUid(18);
//////		System.out.println(levelInfoByUid.toString());
////		Integer topicNumByID = tdao.getUserReplyNumByID(19);
////		System.out.println(topicNumByID);
//		SensitiveDao d=new SensitiveDao();
//		System.out.println();
//	}
//	public static void main(String[] args) {
////		boolean f = isCanReverse("youzan;zanyou");
//		int[] arr={2,1,3,5};
//		boolean f = isCanSkip(arr);
//		System.out.println(f);
////		isp();
//	}
	public static void main(String[] args) {
		int a=1<<31;
		System.out.println(a);
//		double n=Math.log(1534236469)/Math.log(2);
//		System.out.println(n);
	}
//	public static void main(String[] args){
//        Scanner sc=new Scanner(System.in);
//        String s=sc.nextLine();
//        String[] sArr=s.split(",");
//        int arr[]=new int[sArr.length];
//        for(int i=0;i<sArr.length;i++){
//            arr[i]=Integer.parseInt(sArr[i]);
//        }
//        for(int i=1;i<arr.length;i++){
//            int insertVal=arr[i];
//            int insertIndex=i-1;
//            while(insertIndex>=0 && insertVal<arr[insertIndex]){
//                insertIndex++;
//            }
//            arr[insertIndex+1]=insertVal;
//        }
//        //排序结束
//        System.out.println(arr[arr.length-3]);
//    }
	private static void isp(){
		Scanner s = new Scanner(System.in);
        String a = s.nextLine();
        s.close();
        String[] m = a.split(";");
        if(m[0].length()!=m[1].length()){
            System.out.print(false);
            return;
        }
        m[0] = m[0]+m[0];
        System.out.print(m[0].contains(m[1]));
	}
	private static boolean isCanSkip(int[] a){
		boolean o=false;
	    int count=0;
	    int k=0;
	    while(count<=a.length){
	        count++;
	        k+=a[k];
	        if(k<0||k>=a.length){
	            o=true;
	            break;
	        }
	    }
	    return o;
	}
	private static boolean isCanSkipFromArr(int[] arr) {
		if(arr.length<=0){
			return false;
		}else if(arr.length==1){
			if(arr[0]==0){
				return false;
			}else{
				return true;
			}
		}else{
			int first=arr[0];
			int index=0;
			if(first<0){
				return true;
			}else if(first==0){
				return false;
			}else{
				try {
					while(index<arr.length){
						index++;
						first=arr[first];
					}
					return false;
				} catch (Exception e) {
					return true;
				}
				
			}
		}
		
	}
	public static boolean isCanReverse(String str){
		String[] arr=str.split(";");
	    String a=arr[0];
	    String b=arr[1];
	    char charOfB = b.charAt(0);
	    char charOfA = a.charAt(0);
	    //找到b字符里a字符的首字符位置
	    int i1=0,i2=0;
	    
	    String a1="";//zan
	    int indexCharOfB = a.indexOf(charOfB);
	    a1=a.substring(indexCharOfB);
	    
	    String b1="";//z
	    int indexCharOfA = b.indexOf(charOfA);
	    b1=b.substring(0,indexCharOfA);
	    
	    //判断a字符里有没有b1
	    if(!a1.equals(b1)){
	    	return false;
	    }else{
	    	return true;
	    }
	}
}
