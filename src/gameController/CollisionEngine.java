package gameController;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionEngine {

	// Überprüft sämtliche Kollisionen und gibt true zurück, wenn eine Kollision
	// erkannt wird
	public ArrayList<Integer[]> searchCollisions(ArrayList<Rectangle> hitboxes,
			ArrayList<ArrayList<Rectangle>> opponentHitboxes) {
		ArrayList<Integer[]> collisions = new ArrayList<Integer[]>();
		// 0 = UpperBody, 1 = LowerBody, 2 = LeftHand, 3 = RightHand,
		// 4 = XLeftFoot, 5 = XRightFoot
		// XRightFoot - Koordinaten!!!
		for (int i = 0; i < opponentHitboxes.size(); i++) {
			// topCollision
			if (hitboxes.get(0).intersects(opponentHitboxes.get(i).get(0))) {
			}

			// bottomCollision
			if (hitboxes.get(1).intersects(opponentHitboxes.get(i).get(0))) {

				collisions.add(new Integer[] { 3, i, opponentHitboxes.get(i).get(0).x });
			}

			// leftHand
			if (hitboxes.get(2).intersects(opponentHitboxes.get(i).get(0))) {

				collisions.add(new Integer[] { 0, i, opponentHitboxes.get(i).get(0).x });
			}

			// leftFoot
			if (hitboxes.get(4).intersects(opponentHitboxes.get(i).get(0))) {
				collisions.add(new Integer[] { 4, i, opponentHitboxes.get(i).get(0).x });
			}

			// rightHand
			if (hitboxes.get(3).intersects(opponentHitboxes.get(i).get(0))) {
				collisions.add(new Integer[] { 1, i, opponentHitboxes.get(i).get(0).x });
			}

			// rightFoot
			if (hitboxes.get(5).intersects(opponentHitboxes.get(i).get(0))) {
				collisions.add(new Integer[] { 5, i, opponentHitboxes.get(i).get(0).x });
			}

			// ALL Collision
			// XRight vom player >= XLeft vom Component
			// if (playerCoordinates[1] >= componentX.get(i)[0]
			// // XLeft vom player =< XRight vom Component
			// && playerCoordinates[0] <= componentX.get(i)[1]
			// // YTop vom player =< YBottom vom Component
			// && playerCoordinates[2] <= componentX.get(i)[3]
			// // YBottom vom player >= YTop vom Component
			// && playerCoordinates[3] >= componentX.get(i)[2]) {
			//
			// if (playerCoordinates[2] > componentX.get(i)[2]) {
			// System.out.println("TopCollision");
			// collisions.add(new Integer[] { 2, i });
			// } else if (playerCoordinates[3] < componentX.get(i)[3]) {
			// System.out.println("BottomCollision");
			// collisions.add(new Integer[] { 3, i });
			// } else if (playerCoordinates[1] < componentX.get(i)[1]) {
			// System.out.println("LeftCollision");
			// collisions.add(new Integer[] { 1, i });
			// } else if (playerCoordinates[0] > componentX.get(i)[0]) {
			// System.out.println("RightCollision");
			// collisions.add(new Integer[] { 0, i });
			// }
			// }
		}

		return collisions;
	}
}
