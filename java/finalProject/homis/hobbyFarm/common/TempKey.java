package finalProject.homis.hobbyFarm.common;

import java.util.Random;

public class TempKey {
	private String ranText ;
	
    public TempKey() {
    	Random rnd = new Random() ;
    	int		ranNum = rnd.nextInt(900000)+100000 ; // 100000 부터 999999까지
		char[]	ranArr = new char[4] ;
		
		for(int i=0; i<ranArr.length; i++) {
			ranArr[i] = (char)((int)(rnd.nextInt(26))+65) ; // 대문자 4글자
			System.out.print(ranArr[i]) ;
		} String ranStr = new String(ranArr) ;
		
		ranText = ranNum+""+ranStr ;
    }
    
    public String getKey() {
    	return ranText ;
    }
}