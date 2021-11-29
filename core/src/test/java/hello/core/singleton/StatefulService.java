package hello.core.singleton;

public class StatefulService {

//    private int price; //상태를 유지하는 필드
    //공유되는 필드이기에 특정 클라이언트가 값을 변경한다.

    //지역변수를 이용해서 해결
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
//        this.price = price;
        return price;
    }

//    public int getPrice() {
//        return price;
//    }
}
