package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final String KEY_NOT_FOUND_MESSAGE_FORMAT = "La clé %s à la " +
            "position %s ne respecte pas le format";
    public static void main(String[] args) {

//        exercice1();

        exercice2();
    }

    private static void exercice2() {
//        Lire le titre, type,artist, publication, rating
        ArrayList<Song> songs = new ArrayList<>();

        var scanner = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {

            System.out.println("Entrer le titre de l'album");
            String titre = scanner.nextLine();

            System.out.println("Entrer le type de l'album");
            String type = scanner.nextLine();
            System.out.println("Entrer l'artist de l'album");
            String artist = scanner.nextLine();
            System.out.println("Entrer la date de publication de l'album");
            long publication = scanner.nextLong();
            System.out.println("Entrer le rating de l'album");
            long rating = scanner.nextLong();
            Song song = new Song(titre, artist, type, publication, rating);
            songs.add(song);
        }

        var albumsArray = (JSONArray) JSONValue.parse("[]");
        for (Song song : songs) {
            JSONObject songObject = new JSONObject();
            songObject.put("title", song.getTitle());
            songObject.put("artist", song.getArtist());
            songObject.put("type", song.getType());
            songObject.put("publication", song.getPublication());
            songObject.put("rating", song.getRating());
            albumsArray.add(songObject);
        }

        JSONObject collectionObject = new JSONObject();
        collectionObject.put("collection", albumsArray);

        try {
            Files.writeString(Path.of("albums-preferees.json"), collectionObject.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exercice1() {
        try {
            String fileContent = Files.readString(Path.of("collection.json"));
            Object obj = JSONValue.parse(fileContent);
            if (obj instanceof JSONObject jsonObject) {
                ArrayList<Song> songs = mapToSongs((JSONArray) jsonObject.get("collection"));

                int nbAlbums = countNbAlbums(songs);
                System.out.println("Mon nombre d'albums est : " + nbAlbums);

                int nbSingles = countNbSingles(songs);
                System.out.println("Mon nombre des singles est : " + nbSingles);

                afficherListeAlbumsDepuis2003(songs);

                afficherRatingAvec5(songs);
            } else {
                System.out.println("Le fichier n'est pas un jsonObject");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier");
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

    private static void afficherListeAlbumsDepuis2003(ArrayList<Song> songs) {
        System.out.println("\nListe des albums depuis 2003");

        int nbAlbumDepuis2003 = 0;
        for (Song song : songs) {
            if (song.getPublication() >= 2003) {
                nbAlbumDepuis2003++;
                System.out.println("Titre : " + song.getTitle());
            }
        }

        if (nbAlbumDepuis2003 == 0) {
            System.out.println("Aucun album depuis 2003");
        }
    }

    private static void afficherRatingAvec5(ArrayList<Song> songs) {
        System.out.println("\nListe des albums avec rating 5");

        int nbRating5 = 0;
        for (Song song : songs) {
            if (song.getRating() == 5) {
                nbRating5++;
                System.out.println("Titre : " + song.getTitle());
            }
        }

        if (nbRating5 == 0) {
            System.out.println("Aucun album depuis avec rating 5");
        }
    }


    private static int countNbSingles(ArrayList<Song> songs) {
        int nbSingles = 0;
        for (Song song : songs) {
            if (song.getType().equals("single")) nbSingles++;
        }

        return nbSingles;
    }

    private static int countNbAlbums(ArrayList<Song> songs) {
        int nbSongs = 0;
        for (Song song : songs) {
            if (song.getType().equals("album")) nbSongs++;
        }

        return nbSongs;
    }

    /**
     * Cree un arrayList de Songs a partir d'un jsonArray et le retourne ce
     * arraylist
     * @param songArray
     * @return
     */
    private static ArrayList<Song> mapToSongs(JSONArray songArray) {
        verifierFormatSongs(songArray);
        ArrayList<Song> songs = new ArrayList<>();

        for (int i = 0; i < songArray.size(); i++) {
            JSONObject songObject = (JSONObject) songArray.get(i);
            Song song = new Song();
            song.setTitle((String) songObject.get("title"));
            song.setArtist((String) songObject.get("artist"));
            song.setType((String) songObject.get("type"));
            song.setPublication((long) songObject.get("publication"));
            song.setRating((long) songObject.get("rating"));

            songs.add(song);
        }

        return songs;
    }

    /**
     * Verifie si l'object songArray a le format correct
     * sinon lance une exception de type JSONException
     * @param songArray
     */
    private static void verifierFormatSongs(JSONArray songArray) {
        for (int i = 0; i < songArray.size(); i++) {
            Object obj = songArray.get(i);
            if ( !(obj instanceof JSONObject))
                throw new JSONException("Le format json n'est pas correct");

            var jsonObject = (JSONObject) obj;
            if ( !(jsonObject.get("title") instanceof String))
                throw new JSONException(String.format(KEY_NOT_FOUND_MESSAGE_FORMAT, "title", String.valueOf(i)));
            if ( !(jsonObject.get("artist") instanceof String))
                throw new JSONException(String.format(KEY_NOT_FOUND_MESSAGE_FORMAT, "artist", String.valueOf(i)));
            if ( !(jsonObject.get("type") instanceof String))
                throw new JSONException(String.format(KEY_NOT_FOUND_MESSAGE_FORMAT, "type", String.valueOf(i)));
            if ( !(jsonObject.get("publication") instanceof Long))
                throw new JSONException(String.format(KEY_NOT_FOUND_MESSAGE_FORMAT, "publication", String.valueOf(i)));
            if ( !(jsonObject.get("rating") instanceof Long))
                throw new JSONException(String.format(KEY_NOT_FOUND_MESSAGE_FORMAT, "rating", String.valueOf(i)));
        }
    }
}