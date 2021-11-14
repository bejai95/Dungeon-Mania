# Assumptions
1. No two entities can occupy the same tile 
2. There can exist multiple exits in a level
3. Interactions can only happen between adjacent entities -> But this is not what the spec says? 
4. Bows use 1 arrow every time they attack
5. Player always exists is our assumption
6. Assuming that there can only be one game being accessed by a controller at a time. This game was either loaded or created by that controller. Also assuming that the frontend will not call saveGame when there is not a game currently being accessed (makes sense when you think about it). 
7. Assuming that the String id given to saveGame does not have the extension (e.g. .json) on the end, we will take care of that ourselves
8. Assuming that only one unpicked up item can be on a square at a time
9. Assuming that if the player tries to move but cannot move (e.g hits a wall), a tick will still go by
10. Assuming that if a bow is able to be built with both recipes (ie materials are available for both recipes) then the treasure will be used rather than the key
11. When crafting an item it retains the itemId of the unpickedupitem entity.
12. When crafting an item with multiple recipes it will choose to build the first recipe that it comes across unless it is between a  recipe with a key or a treasure, the treasure one will be picked first.
13. A player can only battle one entity per tick.
14. Assumed that the defense multiplier that occurs from stacked shields and armour can not go below 
0 because that would mean enemies do negative damage
14. Assumed characters have a base amount of damage (this was to ensure characters dont 
immediately die when they run into an enemy when they spawn in
15. Assumed bow multipler occurs after all damage aggregate it calculated
16. Design choice: made item id and unpickedupitem that same as to reuse ids so no conflicts occur
17. There will always be at least one cardinally adjacent square to the zombie spawner 
18. The blast radius of the bomb is 1 cell in each direction 