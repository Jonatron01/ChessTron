To Run:
run 'mvn spring-boot:run' in the project and open localhost in your browser to use the GUI.

Information:
This is a built-from-scratch implementation of a chess engine. The search depth is 6 until the endgame where it can go up to 10. Since the 
depth of the engine is not the highest, it tends to play mostly tactical moves and neglects its pawns in the middlegame. Despite this, it
still feels relatively strong since it can see any tactics within 6-plys that will gain material and easily sees moves that may be harder 
for a human to see. The engine visits roughly 50k nodes per second. 
