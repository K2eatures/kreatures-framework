at_hq.
energy_8.
thunderstorm.
sun.
pre_clouds.
low :- energy_8, energy_4.
energy_4:- energy_8.
storm_or_rain :- slow.
slow:- thunderstorm.

-on_the_way :- at_site.
-at_cave:- at_site.
at_site :- at_hq.




