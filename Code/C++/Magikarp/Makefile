CC = gcc
CFLAGS = -O3 -Wall

PROGRAMS = bm_server bm_widget dealer example_player base_player magikarp

RESULTS = matchName.log matchName.player1.std matchName.player1.err matchName.err matchName.player0.std matchName.player0.err

all: $(PROGRAMS)

clean:
	rm -f $(PROGRAMS)

rm_results:
	rm -f $(RESULTS)




bm_server: bm_server.c game.c game.h rng.c rng.h net.c net.h
	$(CC) $(CFLAGS) -o $@ bm_server.c game.c rng.c net.c

bm_widget: bm_widget.c net.c net.h
	$(CC) $(CFLAGS) -o $@ bm_widget.c net.c

dealer: game.c game.h evalHandTables rng.c rng.h dealer.c net.c net.h
	$(CC) $(CFLAGS) -o $@ game.c rng.c dealer.c net.c

example_player: game.c game.h evalHandTables rng.c rng.h example_player.c net.c net.h
	$(CC) $(CFLAGS) -o $@ game.c rng.c example_player.c net.c

base_player: game.c game.h evalHandTables rng.c rng.h base_player.c net.c net.h
	$(CC) $(CFLAGS) -o $@ game.c rng.c base_player.c net.c

magikarp: game.c game.h evalHandTables rng.c rng.h magikarp.c net.c net.h
	$(CC) $(CFLAGS) -o $@ game.c rng.c magikarp.c net.c


