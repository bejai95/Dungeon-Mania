[Assumptions]
No two entities can occupy the same tile 
There can exist multiple exits in a level
Interactions can only happen between adjacent entities
Bows use 1 arrow every time they attack
Player always exists is our assumption
Assuming that there can only be one game being accessed by a controller at a time. This game was either loaded or created by that controller. Also assuming that the frontend will not call saveGame when there is not a game currently being accessed (makes sense when you think about it). 
Assuming that the String id given to saveGame does not have the extension (e.g. .json) on the end, we will take care of that ourselves
