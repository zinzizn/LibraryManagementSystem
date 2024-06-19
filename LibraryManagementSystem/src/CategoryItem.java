
public class CategoryItem {
	int id;
	String name;
	
	public CategoryItem(int i,String n) {
		this.id=i;
		this.name=n;
	}
	public String toString() {
		return name;
	}
	 public String getCategoryName() {
         return name;
     }
}
