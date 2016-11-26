/**   
* @Title: Population.java 
* @Package tsp 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author FlyingFish
* @date 2016-11-21
* @version V1.0   
*/
package tsp;

/**
* <p> Population</p>
* <p>Description: </p>
* @author FlyingFish
* @date 2016-11-21
*/
public class Population {

	private Individual[] population;
	
	private int elite,weakest;
	
	private double sumOfFitness,averageOfFitness;
	


	
	public Population(Individual[] popul){
		this.population = popul;
	}
	
	public void statisticsIndivInfo(){
		int eliteIndex = -1,weakest = -1;
		double eliteFitness = -0.1,weakestFitness = Integer.MAX_VALUE;
		for(int i = 0;i < population.length;i ++)
			if(population[i].fitness > eliteFitness){
				eliteIndex = i;
				eliteFitness = population[i].fitness;
			}
		for(int i = 0;i <population.length;i ++)
			if(population[i].fitness < weakestFitness){
				weakest = i;
				weakestFitness = population[i].fitness;
			}
		this.elite = eliteIndex;
		this.weakest = weakest;
	}
	
	public void staticsticsWholeInfo(){
		double sum = 0;
		for(int i = 0; i < population.length;i ++)
			sum+= population[i].fitness;
		this.sumOfFitness = sum;
		this.averageOfFitness = sum / population.length;
	}
	
	public void showInfo(){
		System.out.println("��Ⱥ��Ϣ");
		System.out.print("��ģ��" + population.length
									+"\n��ǿ���壺" + elite + "  " + population[elite].fitness
									+"\n�������壺" + weakest+"  " + population[weakest].fitness
									+"\nƽ����Ӧ��" + this.averageOfFitness
									+"\n����Ӧ�ȣ�"+this.sumOfFitness);
		System.out.println("\nȫ����������(��0��ʼ���)��");
		for(int i = 0; i < population.length;i ++)
			System.out.printf("\n%-5d%-10.2f%s", i,population[i].fitness,population[i].chromosome);
		System.out.println();
	}
	
	public int getPopulationScale(){
		return population.length;
	}
	
	public Individual getIndividual(int i){
		return population[i];
	}
	
	public void setIndividual(int i,Individual indiv){
		population[i] = indiv;
	}
	
	/**
	 * @return the population
	 */
	public Individual[] getPopulation() {
		return population;
	}

	/**
	 * @return the elite
	 */
	public int getElite() {
		return elite;
	}

	/**
	 * @return the weakest
	 */
	public int getWeakest() {
		return weakest;
	}

	/**
	 * @return the sumOfFitness
	 */
	public double getSumOfFitness() {
		return sumOfFitness;
	}

	/**
	 * @return the averageOfFitness
	 */
	public double getAverageOfFitness() {
		return averageOfFitness;
	}
	
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param args 
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���� ��Ⱥ����Ϣ�Ƿ������ȷ
		Individual i1 = new Individual(1.0);
		Individual i2 = new Individual(4.5);
		Individual i3 = new Individual(3.5);
		
		Population p = new Population(new Individual[]{i1,i2,i3});
		p.statisticsIndivInfo();
		p.staticsticsWholeInfo();
		System.out.println("Elite: " + p.getElite() + "  Weakest: " + p.getWeakest()
										+"\nSum of Fitness: " + p.getSumOfFitness() + " \nAverage Of Fitness: " + p.getAverageOfFitness());
		
		
	}

}
