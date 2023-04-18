package main;

public class MainClass {
    public static void main(String[] args) {
        new Game();
    }
}
//Class trong contructor của class khác đơn giản để lấy quyền truy cập vào class đó

//Ghi chú các hoạt động của game

//Hoạt ảnh
/*
Chứa tất cả ảnh trong một array khi game chạy thì sẽ có bộ đếm thời gian 
-->khi thời gian đạt đến gtri nào đó thì ta vẽ lại ảnh và tăng index array lên 
-->Tạo vòng lặp ta có animation
*/

//UPS
/*
Take care of logic ( player move, event..)

Không thể thay đổi như fps luôn cùng một giá trị
Để bắt các lỗi chương trình khi bị lag
Vì sử dụng gameloop now - lastFrame >= timePerFrame ==> sẽ bị mất 1 frame nếu như nó lớn hơn thì vì bằng ==> cộng dồn lên thì sẽ mất hiệu năng
-->Để tạo ra một gameloop tốt hơn
*/

//Vấn đề khi sử dụng boolean cho moving khi ta hold key xong switch tab thì sẽ bị hold key đó mãi mà ko thoát đc 
//--> Để khắc phục thêm code để xác định khi nào lost focus thì set all boolean thành false

/*
--Cách vẽ level
1.Ta có bức ảnh level chia làm các phần cấu tạo nên nó 
VD như chia làm 48 viên gạch(tiles) để lát nền 
2.Ta có một nền nhà chính là level mà bức ảnh đó được chỉnh các phần ta muốn lát loại gạch nào cùng 1 màu
-->Khi đó ta sử dụng getRGB để chọn màu (chọn ra đc 3 màu nên phần gạch để lát level sẽ là 1 màu VD đỏ -> getRed())
-->Chọn đc màu sẽ trả về giá trị khoảng 0->255 ta lấy làm index luôn
Khi lấy đc index thì ta sẽ lát gạch(tiles) tương ứng với index của nó 
Cụ thể, mỗi ô trong level được biểu diễn bằng một pixel trong hình ảnh đại diện cho level. Màu của pixel đó được sử dụng để biểu thị giá trị của ô đó trong level.

Để chuyển đổi màu thành giá trị của ô, phương thức sử dụng đối tượng Color để lấy giá trị của thành phần màu đỏ (red component) của màu tại vị trí pixel đó.
Nếu giá trị đó lớn hơn hoặc bằng 48 (được chọn bởi điều kiện if(value >= 48)), giá trị của ô đó sẽ được xác định là 0, nghĩa là ô đó sẽ không có gì được vẽ lên đó.
Nếu giá trị đó nhỏ hơn 48, giá trị của ô đó sẽ được giữ nguyên và sẽ được sử dụng để xác định loại sprite nào sẽ được vẽ lên đó.
====>Ta được 1 level 
3.Bằng cách này cùng 1 loại gạch(tiles) thì chỉ cần đổi màu sàn ta sẽ đc các loại level khác nhau
 */


/*HIT BOX
ở chat gpt
 */

/*Gravity & jump
 Bình thường thì sẽ lấy tọa độ của tile bên cạnh (tùy trường hợp mà trái hay phải) để tính toán hitbox
 -->Giống như hitbox sẽ có 1 khoảng cách theo titlesize mặc định nên ta cần bỏ nó đi (như ở hàm getEntityNextToWall để lấy giá trị tọa độ tường bên cạnh và xóa khoảng cách)
 
 +Check xem có in air ko bằng x and y
 */

/* Game states (trạng thái trò chơi)
ví dụ như đang chơi hay pause để hiện ra option pause
-Sử dụng enum để store data (giống constaint) để chứa state
ví dụ như 1 là draw và update only playing scene... 2 là menu scene 
	+Trong Java, enum (viết tắt của từ "enumeration") là một kiểu dữ liệu đặc biệt dùng để định nghĩa một tập hợp các hằng số có thể sử dụng trong chương trình.

	Ví dụ, nếu bạn muốn định nghĩa một tập hợp các màu sắc, bạn có thể sử dụng enum để định nghĩa các hằng số cho các màu sắc đó như sau:
	enum Color {
    RED, GREEN, BLUE;
	}  -->Khi gọi chỉ cần gọi Color.RED tg tự ta có Gamestate.Playing

Trong game này
mọi thứ bắt đầu từ game class khi ta update hoặc render phụ thuộc vào gamestate ta 
đi vào các phần đó  -->Đó là cách gamestate hoặc động
 */
/*
 URM button là nút replay level và nút home
 */

/*
 Bigger level
 1 level tại default size của lvl trên màn hình ta set để khi người chơi vượt quá 80% của 
 kích cỡ màn hình hiển thị thì ta sẽ vẽ lvl đó nhưng dịch sang số px cố định theo player move speed 
 -->ta được 1 level to có thể vẽ đúng chỉ vào phần player đang đứng (tất nhiên khi chạm 2 cạnh của map thì sẽ dừng việc move lvl lại)
 vd level có width = 1000px ta đi đến 805px --> vẽ di chuyển 5px sang bên, tương tự cứ di chuyển tiếp sang bên thì ta sẽ vẽ dịch sang 
 
 Để làm đc ta ko cộng từng phần của lvl mà ta thay đổi cách vẽ level đó
 drawPlayer(x - lvlOffset, y , w, h)
 
 */

//Enemies
/* offset : bù lại
 3 Part 
 1.Add enemy, animation
 các bước tương tự như player nhưng ta thêm nhiều enemy nên dùng arraylist
 và vẽ kẻ thù bằng màu green thay vì red
 
 2.hit box, patrol //tuần tra
 3.see player && attack
behavior : hành vi
Giải thích bằng ảnh trong folder picture/gtvt
 */

/*Combat (player attack/health/power... & enemy die)
1.Vẽ thanh máu và năng lượng (status bar) trong player
2.Player attack hitbox & enemy attack player hitbox too cg trong player
	-Lấy attack box 
	-Xoay người khi di chuyển bằng cách đặt width âm thì vẫn vẽ từ điểm (x,y) nhưng vẽ ngược lại
	g.drawImage(animations[playerAction][aniIndex],
        			(int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, -->flipX sẽ âm nếu quay đầu 
        			(int)(hitbox.y - yDrawOffset), 
        			width * flipW, height, null);  -->flipW thì chỉ có giá trị là 1 hoặc -1
	
	--Tương tự vs enemey(crab) từ attackbox, flip.....

3.Game over class
4.Tương tác giữa tấn công
	- tạo máu và dmg của địch xong khi hitbox chạm vào thì trừ máu ở changeHealth()
	- attackBox.intersects(c.getHitBox()) -->hàm này trả về true nếu 2 hit box overlap nhau 
	- Nếu enemy chết thì set active = false
	-Vẽ gameover cùng lúc vô hiệu các tính năng khác của player 
	
*/

/*
 Multilevel 
 1.Vẽ overlay cho lvl compelete trc 
 -->Giống như pause hay menu thôi
 -Lấy lvl thay vì lấy thẳng từ biến static trong loadsave ta thấy từ mảng các lvl
 Lấy từ mảng nên phải dùng folder thay vì đường dẫn trực tiếp trong file res nên sẽ có khác biệt (trong getAllLevels)
 
 
 -Thay đổi levels class
 +Thay đổi cách tính lvlOffsetX vì dài ngắn khác nhau
 +Thay đổi hàm tạo trong Level thay vì get lvlData h sẽ lấy img và tạo các data trong
 		createLevelData();
		createEnemies();
		calcLvlOffsets();
		
 -Thay đổi lvl Manager
  +Thay lvlOne thành levels.get(lvlIndex) và sửa lEVELONE thành arraylist levels
 -Thay đổi trong playing
 	+Trong contructor thêm calc Lvl Offet vì mỗi lvl khác nhau 
 	
-Thay đổi trong Enemy manager
-Tạo method thay đổi spawn point cho player tùy vào mỗi lvl trong helpmethod (mặc dù chưa dùng :)
 */













