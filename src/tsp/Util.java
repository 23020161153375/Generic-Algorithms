package tsp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
* <p> Util ������</p>
* <p>Description: </p>
* @author GJS,FlyingFish
* @date 2016-10-29
* @see<a href ="http://www.cnblogs.com/GuoJiaSheng/p/4192301.html">GJS��ҳ</a>
*/
public class Util {

	/**
	 * <p>��ȡ�ļ���</p>
	 * �ļ���ʽΪTSP��������tsplib�ṩ������Դ��
	 * @param path tsp�ļ�·��
	 * @return
	 */
	public static CityPoint[] ReadFile(String path) {
		CityPoint[] point = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String tempString;
			for (int i = 0; i < 5; i++) {
				reader.readLine();
				if (i == 2) {//�������������
					tempString = reader.readLine();
					
					int index=tempString.indexOf(":");
					
					String DIMENSION = tempString.substring(
							index+2, tempString.length());
					point = new CityPoint[Integer.valueOf(DIMENSION)]; // ���и���
				}
			}

			int index = 0;
			// ��ȡ����
			while ((tempString = reader.readLine()) != null) {
				if (index == point.length)
					break;
				//ÿ�����е������ʽΪ :��� X���� Y����
				String ar[] = tempString.split(" ");
				int x = 0, tempx = 0, tempy = 0;
				for (int i = 0; i < ar.length; i++) {
					if (!ar[i].equals("")) {
						if (x == 1) {//������Ų����棬����X��1��ʼ
							tempx = Integer.valueOf(ar[i]);
						} else if (x == 2) {
							tempy = Integer.valueOf(ar[i]);
						}
						x++;
					}
				}
				CityPoint pointTemp = new CityPoint(tempx, tempy);
				point[index] = pointTemp;
				index++;
				// pointTemp.x=

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return point;
	}

	/**��Ը���������Դ�����ļ��ж�ȡ������������з������ļ���ʽΪopt.tour������tsplib�ṩ��
	 * @parameter file opt.tour�ļ�·��*/
	public ArrayList<Integer>GetBest(String file)
	{
		ArrayList<Integer>best=new ArrayList<Integer>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String	tempString= "";
			//����ǰ5�е�˵��
			 reader.readLine();
			 reader.readLine();
			 reader.readLine();
			 reader.readLine();
			 reader.readLine();
	
			// ��ȡ����
			while ((tempString = reader.readLine()) != null) {
				if (tempString.equals("-1"))//��-1���ǳ��б�����ı�־
					break;
				//�ó��еı�Ŵ�0��ʼ
				best.add(Integer.valueOf(tempString)-1);
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return best;
	}
	
	/**
	 * �����������еľ���
	 * @param begin
	 * @param end
	 * @return
	 */
	public static double Distance(CityPoint begin,CityPoint end)
	{
		double y=Math.abs(begin.y-end.y);
		double x=Math.abs(begin.x-end.x);
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	
	public static double[][] Distance(CityPoint[] cities){
		double[][] distanceMatrix = new double[cities.length][cities.length];
		for(int i = 0;i < cities.length;i ++)
			for(int j = 0;j <cities.length; j++)
				distanceMatrix[i][j] = Distance(cities[i],cities[j]);
		return distanceMatrix;
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*
		String FilePath = "E://a280.tsp";
		Util.ReadFile(FilePath);
        Util test=new Util();
        test.GetBest("E://att48.opt.tour");
        SA SA=new SA(1000,0.9);
     System.out.print(SA.DistanceCost(test.GetBest("E://att48.opt.tour")));
       */ 
        //����Distance
        CityPoint[] cities = new CityPoint[3];
        cities[0] = new CityPoint(0,0);
        cities[1] = new CityPoint(3,0);
        cities[2] = new CityPoint(0,4);
        double[][] dis = Distance(cities);
        for(int i = 0;i < dis.length;i ++){
        	for(int j = 0; j < dis[i].length;j ++)
        		System.out.print(dis[i][j] + " ");
        	System.out.println();
    	}
	}

}
