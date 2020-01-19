first ,this message appears:

![](https://scontent.fsdv3-1.fna.fbcdn.net/v/t1.0-9/82229225_2742047189196303_1055998467991666688_n.jpg?_nc_cat=102&_nc_ohc=oON0rJ5zSyYAX_P3Qlm&_nc_ht=scontent.fsdv3-1.fna&oh=da40ad88c7d7adaf803b2603d928c86f&oe=5E92F73B)

the costumer choose which scenario he want to play with. [0,23]

then, this message appears:

![](https://scontent.fsdv3-1.fna.fbcdn.net/v/t1.0-9/83191049_2742047192529636_6234762186879664128_n.jpg?_nc_cat=110&_nc_ohc=S5zvMGWkEpsAX-ZSqdk&_nc_ht=scontent.fsdv3-1.fna&oh=8c65dbfcc88097002552ab37e996da6d&oe=5E973DE1)

the costumer choose which type of game he want to play: automatic or by mosue.

if the costumer choose "automatic" type of game- the game starts.  (using the functions at the MyMangaeGame class).

else, this message would be appears:

![](https://scontent.fsdv3-1.fna.fbcdn.net/v/t1.0-9/82128309_2742047185862970_4425296596833402880_n.jpg?_nc_cat=102&_nc_ohc=UDf5h4QGLrEAX91Kc6J&_nc_ht=scontent.fsdv3-1.fna&oh=0cbcd4cc48d0b9e8c8f5b5c74774b36f&oe=5ED4A27E) 

the costumer have to choose which node the robots place at the beggining of the game.
and than, he starts to play by mouse.
every step of the robots he choose by this message:

![](https://scontent.fsdv3-1.fna.fbcdn.net/v/t1.0-9/82024788_2742063555861333_5134363648357040128_n.jpg?_nc_cat=108&_nc_ohc=Wnap3uulSl0AX_QSXco&_nc_ht=scontent.fsdv3-1.fna&oh=92de9bd8079776927d29c91800a226f3&oe=5ED57D9C)

and this is the final screen:

![](https://scontent.fsdv3-1.fna.fbcdn.net/v/t1.0-9/82643907_2741991509201871_1537314721047248896_n.jpg?_nc_cat=104&_nc_ohc=XxtWDaN5EH0AX9-zfvH&_nc_ht=scontent.fsdv3-1.fna&oh=3bdd22d3b3e961a2d26f60072479c061&oe=5EA0E831)

KML Logger class:
The following methods are implemented:
1.KML_logger() - single constractor which constructs the header or beggining of the kml file structure - see code for more info.
2.Styleid(FileWriter fw,String styleid) - this function sets the icons of the kml (in our case it is apple,banana and robot icons)
3.fillFruit(FileWriter fw,String position,String fruit) - here we add the fruits to the file one by one while game is running.
4.fillPlacemark(FileWriter fw,String position) - adds the movements that is need to be done to the file one by one while game is running
5.fillNodes(FileWriter fw,String position) - fills the nodes of the graph
6.fillEdges(FileWriter fw,String coordinates) - fills the edges of all the graph (the RED line in the final KML file)
7.openKMLFolder(FileWriter fw,String foldername,String description) - opens a folder provided by foldername and fills a description by a description string.
8.closeKMLFolder(FileWriter fw) - closes the last opened folder.
