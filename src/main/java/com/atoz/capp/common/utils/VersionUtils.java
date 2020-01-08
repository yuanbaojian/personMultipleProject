
package com.atoz.capp.common.utils;


/**
 * 版本计算工具类
 * Created at 2017-08-03
 * Created by caicai.gao
 */
public class VersionUtils {
	
	/**
	 *  计算新的大版本
	 *  @param  previousVersion  前大版本号
	 *  @return 新大版本号
	 */
	public static String getNewMajorVersion(String previousVersion) {
		//版本生成规则：第一次发放为A版，原则上以后更改均采用换版的形式，按A→B→C∙∙∙∙∙∙→ Y→AA ∙∙∙∙∙→AY∙∙∙∙∙→YY顺序更改，其中字母I、O、S、X、Z不能使用
		String newMajorVersion = "";
        //将前版本的字母转化成ASCⅡ中所代表的数字 ,并用preNum 来保存
		char[] c = previousVersion.toCharArray(); 
		char preNum1 = ' ';
	    char preNum2 = ' ';
		if (c.length>1) {
			 preNum1 = previousVersion.charAt(0);
		     preNum2 = previousVersion.charAt(1);
		} else {
			 preNum1 = previousVersion.charAt(0);
		}
       
        if (preNum2==' ') {
            if (preNum1 == 'Y') {
            	preNum1 = 'A';
            	preNum2 = 'A';
            	newMajorVersion = String.valueOf(preNum1) + String.valueOf(preNum2);
            } else {
                int newInt = preNum1+1;
                //在版本中字母I、O、S、X、Z不能使用
                if((char)(newInt) == 'I' || (char)(newInt) == 'O' || (char)(newInt) == 'S'|| (char)(newInt) == 'X'|| (char)(newInt) == 'Z') {
                	newInt = newInt + 1;
                	preNum1 = (char)newInt;
                	newMajorVersion = String.valueOf(preNum1);
                } else {
                	preNum1 = (char)newInt;
                	newMajorVersion = String.valueOf(preNum1);
                }
            }
        	return newMajorVersion; 
        } else {
            if (preNum1 == 'Y' && preNum2 == 'Y') {
            	return newMajorVersion = "";
        	} else {
        		if (preNum2 == 'Y') {
                    int newInt = preNum1+1;
                    //在版本中字母I、O、S、X、Z不能使用
                    if((char)(newInt) == 'I' || (char)(newInt) == 'O' || (char)(newInt) == 'S'|| (char)(newInt) == 'X'|| (char)(newInt) == 'Z') {
                    	newInt = newInt + 1;
                    	preNum1 = (char)newInt;
                    } else {
                    	preNum1 = (char)newInt;
                    }
                	newMajorVersion = String.valueOf(preNum1) + String.valueOf('A');
        		} else {
                    int newInt2 = preNum2+1;
                    //在版本中字母I、O、S、X、Z不能使用
                    if((char)(newInt2) == 'I' || (char)(newInt2) == 'O' || (char)(newInt2) == 'S'|| (char)(newInt2) == 'X'|| (char)(newInt2) == 'Z') {
                    	newInt2 = newInt2 + 1;
                    	preNum2 = (char)newInt2;
                    	newMajorVersion = String.valueOf(preNum1) + String.valueOf(preNum2);
                    } else {
                    	preNum2 = (char)newInt2;
                    	newMajorVersion = String.valueOf(preNum1) + String.valueOf(preNum2);
                    }
        		}
        	}
        	return newMajorVersion; 
        }
	}

	/**
	 *  计算新的小版本
	 *  @param  previousVersion  前小版本号
	 *  @return 新小版本号
	 */
	public static String getNewMinorVersion(String previousVersion) {
		String newMinorVersion;
		//TODO  计算新的小版本
		int  i = Integer.valueOf(previousVersion);
		return newMinorVersion = String.valueOf(i+1);
	}


	public static void main(String[] args) {
		//大版本测试
		//特殊值测试
//		System.out.println("特殊值测试");
//		String[] s = {"H","N","R","W","Y"};
//		for (int i=0;i<s.length;i++) {
//			System.out.println("input:"+s[i]+",output:"+getNewMajorVersion(s[i]));
//		}
//		for (int i=0;i<s.length;i++) {
//			for (int j=0;j<s.length;j++) {
//				System.out.println("input:"+s[i]+s[j]+",output:"+getNewMajorVersion(s[i]+s[j]));
//			}
//		}
		
		//随机测试
//		System.out.println("随机测试");
//		String[] a = {"B","T","AT","AN","BF","BK","KD","JP"};
//		for (int i=0;i<s.length;i++) {
//			System.out.println("input:"+a[i]+",output:"+getNewMajorVersion(a[i]));
//		}
		System.out.println(getNewMajorVersion(""));
		//小版本测试
//		System.out.println("小版本测试");
//		String preMinorVersion = "1";
//		System.out.println("input:"+preMinorVersion+",output:"+getNewMajorVersion(preMinorVersion));
	}
}