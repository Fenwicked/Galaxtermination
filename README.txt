GALAXTERMINATION! Game for Game Jam 2018

gameobject object
    create new<
    size x
    size y
    position x
    position y
    motion x
    motion y
    yaw
    yaw speed
    turn<
    move<

field object - gameobject
    number of asteroids
    number of bots
    number of players
    border object
    number of living combatants
    number of gridspaces
    gridspace object

gridspace object - gameobject
    is filled
    grid position x
    grid position y

border object - gameobject
    border sprite

combatant object - gameobject
    shoot<
    check collision<
    is alive
    ship sprite
    number of lives
    player or bot
    shot speed
    shot spread
    has missile
    has overshield
    has radar

shot object - gameobject
    shot sprite
    shape
    power
    is tracking
    total duration
    remaining duration

asteroid object - gameobject
    health
    check collision<
    is alive
    contains powerup
    which powerup
    position z
    spin<
    pitch speed
    roll speed
    pitch
    roll

powerup object - gameobject
    type
    is alive