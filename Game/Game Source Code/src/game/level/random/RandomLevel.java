package game.level.random;

import game.entity.mob.Player;
import game.level.Level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
public class RandomLevel extends Level {

	private final Random random = new Random();

	private ArrayList<Room> rooms = new ArrayList<Room>();
	private ArrayList<Room> linkedRooms = new ArrayList<Room>();
	private ArrayList<Room> bigRooms = new ArrayList<Room>();
	private ArrayList<Room> smallRooms = new ArrayList<Room>();
	private ArrayList<Room> tempRooms = new ArrayList<Room>();
	private ArrayList<Room> corridors = new ArrayList<Room>();

	/**
	 * 1 : sizes of the bounds 2 : sizes of the big rooms 3 : sizes of the small
	 * rooms (big rooms / 2)
	 **/
	private int w1, w2, w3, h1, h2, h3;
	private int bigRoomSize, smallRoomSize;

	private Room[] bounds = new Room[2];

	public RandomLevel(int difficulty) {
		super(difficulty);

		width = 100;
		height = width * 10 / 16;

		tiles = new int[width * height];

		w1 = (int) (width / 2.5);
		h1 = w1 / 2;
		w2 = width / 5;
		h2 = (int) (w2 / 1.618);
		w3 = w2 / 2;
		h3 = h2 / 2;

		bigRoomSize = 5;
		smallRoomSize = 15;

		generateLevel();
	}

	protected void generateLevel() {
		generateBounds();
		generateRooms();

		removeRooms();

		// This is the first room of the linked ones
		linkedRooms.add(rooms.get(0));
		rooms.remove(0);

		linkRooms();
		int counter = 0;
		while (rooms.size() != 0 && counter < 100) {
			counter++;
			addCorridors();
			linkRooms();
		}

		generateTileArray();
	}

	protected void generateBounds() {

		bounds[0] = new Bound(w1, h1, this);

		while (true) {
			// Swaps the places of w1 and h1 so that one bound will "stand" and
			// the other "lie"
			bounds[1] = new Bound(h1, w1, this);

			if (bounds[1].getRectangle().intersects(bounds[0].getRectangle())) {
				int[] sort = new int[4];
				int x0, x1, y0, y1;

				// This could be done in a for lopp but takes longer code and
				// gets less readable
				sort[0] = bounds[0].getX();
				sort[1] = bounds[0].getX() + bounds[0].getWidth();
				sort[2] = bounds[1].getX();
				sort[3] = bounds[1].getX() + bounds[1].getWidth();

				sort = sort(sort);

				x0 = sort[1];
				x1 = sort[2];

				sort[0] = bounds[0].getY();
				sort[1] = bounds[0].getY() + bounds[0].getHeight();
				sort[2] = bounds[1].getY();
				sort[3] = bounds[1].getY() + bounds[1].getHeight();

				sort = sort(sort);

				y0 = sort[1];
				y1 = sort[2];

				// This gets the area of the bounds rects and subtracts the area
				// of the intersection
				int boundsArea = (bounds[0].getWidth() * bounds[0].getHeight()) + (bounds[1].getWidth() * bounds[1].getHeight()) - (x1 - x0) * (y1 - y0);

				// If the bounds cover 30 % - 40 % of the screen, it should
				// break
				if (width * height * 0.3 < boundsArea && boundsArea < width * height * 0.4) {
					break;
				}
			}
		}

	}

	protected void generateRooms() {
		while (bigRooms.size() == 0)
			bigRooms = addRooms(bigRoomSize, w2, h2);
		while (smallRooms.size() == 0)
			smallRooms = addRooms(smallRoomSize, w3, h3);

		// Adds the different rooms to a new array
		rooms.addAll(bigRooms);
		rooms.addAll(smallRooms);
	}

	protected ArrayList<Room> addRooms(int size, int width, int height) {
		ArrayList<Room> rooms = new ArrayList<Room>();

		for (int i = 0; i < size; i++) {
			int counter = 0;
			intersectionDetection: while (true) {
				counter++;
				// "Restarts" the function when the counter is over 100. This is
				// just for when a place for the room is not findable on 100
				// tries. Subtracting 1 from the size makes it easier the next
				// time to find locations for the rooms
				if (counter > 100) return addRooms(size - 1, width, height);

				System.out.println(counter);
				int w = random.nextBoolean() ? width : height;
				int h = (w == width) ? height : width;
				Room r = new Room(w, h, this);

				boolean intersects = false;

				for (Room room : rooms) {
					if (r.getRectangle().intersects(room.getRectangle())) intersects = true;
				}

				if (!intersects) {
					rooms.add(r);
					break intersectionDetection;
				}
			}
		}
		return rooms;
	}

	protected void removeRooms() {
		tempRooms.clear();
		tempRooms.addAll(rooms);
		rooms.clear();

		for (Room r : tempRooms) {

			for (Room b : bounds) {
				if (r.getRectangle().intersects(b.getRectangle())) {
					rooms.add(r);
					break;
				}
			}
		}

		System.out.println(rooms.size() + " : " + tempRooms.size());
	}

	protected int[] sort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < i; j++) {
				if (array[i] < array[j]) {
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}

		return array;
	}

	protected void linkRooms() {
		marking: for (Room r : rooms) {
			for (Room l : linkedRooms) {
				if (r.getRectangle().intersects(l.getRectangle())) {
					linkedRooms.add(r);
					rooms.remove(r);
					break marking;
				}
			}
		}
	}

	protected void addCorridors() {
		int ax = 0, ay = 0, bx = 0, by = 0;
		int indexA = 0, indexB = 0;

		double distance = -1, d;

		for (Room a : rooms) {
			for (Room b : linkedRooms) {
				ax = a.getX() + a.getWidth() / 2;
				ay = a.getY() + a.getHeight() / 2;
				bx = b.getX() + b.getWidth() / 2;
				by = b.getY() + b.getHeight() / 2;

				// Calculates the distance with Pythagora's
				d = Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));

				// Updates distance to be as short as possible
				if (d < distance || distance == -1) {
					distance = d;
					indexA = rooms.indexOf(a);
					indexB = linkedRooms.indexOf(b);
				}
			}
		}

		Room a = rooms.get(indexA);
		Room b = linkedRooms.get(indexB);

		ax = a.getX() + a.getWidth() / 2;
		ay = a.getY() + a.getHeight() / 2;
		bx = b.getX() + b.getWidth() / 2;
		by = b.getY() + b.getHeight() / 2;

		double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		Room finish = null;

		if (ax < bx) {
			x0 = ax - 1;
			x1 = bx + 1;
			y0 = ay - 1;
			y1 = by + 1;
			finish = b;
		}
		// if( ax > bx)
		else {
			x0 = bx - 1;
			x1 = ax + 1;
			y0 = by - 1;
			y1 = ay + 1;
			finish = a;
		}
		// add statement for when ax == bx

		int size = 5;
		double angle, dx, dy;

		int counter = 0;

		while (true) {
			counter++;

			angle = Math.atan2(y0 - y1, x0 - x1);

			angle -= Math.toRadians(150 + random.nextInt(60));

			dx = Math.cos(angle);
			dy = Math.sin(angle);

			Room corridor = new Room((int) x0, (int) y0, size, this);

			try {
				corridors.add(corridor);
			} catch (OutOfMemoryError e) {
				System.out.println(corridors.size());
				e.printStackTrace();
			}

			x0 += dx;
			y0 += dy;

			for (Room r : linkedRooms) {
				if (r.getRectangle().contains(corridor.getRectangle()) && !corridor.equals(r)) {
					corridors.remove(corridor);
				}
			}

			for (Room r : rooms) {
				if (r.getRectangle().contains(corridor.getRectangle()) && !corridor.equals(r)) {
					corridors.remove(corridor);
				}
			}

			if (corridor.getRectangle().intersects(finish.getRectangle())) {
				// Prevents the corridors from being tapered (avsmalnande) at
				// their end
				corridors.add(new Room((int) (x0 + dx), (int) (y0 + dy), size, this));
				break;
			}

			if (counter > 1000) {
				corridors.clear();
				break;
			}

		}

		for (Room r : corridors) {
			if (!linkedRooms.contains(r)) linkedRooms.add(r);
		}

	}

	protected void generateTileArray() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for (Room r : linkedRooms) {
					if (r.getRectangle().contains(x, y)) {
						int color = 0;
						if (random.nextInt(9) == 0) color = 0xffffff00;
						else color = 0xff00ff00;

						tiles[x + y * width] = color;
					}
				}
			}
		}
	}

	public void draw(Graphics g, double scale, Player p) {

		g.setColor(Color.magenta);
		g.fillRect(0, 0, (int) (width * scale), (int) (height * scale));
		g.setColor(Color.green);

		for (int i = 0; i < rooms.size(); i++) {
			Room r = rooms.get(i);
			if (!r.isRemoved()) g.fillRect((int) (r.getX() * scale), (int) (r.getY() * scale), (int) (r.getWidth() * scale), (int) (r.getHeight() * scale));
		}

		g.setColor(Color.blue);

		for (int i = 0; i < linkedRooms.size(); i++) {
			Room r = linkedRooms.get(i);
			if (!r.isRemoved()) g.fillRect((int) (r.getX() * scale), (int) (r.getY() * scale), (int) (r.getWidth() * scale), (int) (r.getHeight() * scale));
		}

		for (int i = 0; i < corridors.size(); i++) {
			Room r = corridors.get(i);
			if (!r.isRemoved()) g.fillRect((int) (r.getX() * scale), (int) (r.getY() * scale), (int) (r.getWidth() * scale), (int) (r.getHeight() * scale));
		}
		
		g.setColor(Color.yellow);
		g.fillRect((int)(p.getX()/16 * scale), (int)(p.getY()/16 * scale), (int)scale, (int)scale);
	}

}