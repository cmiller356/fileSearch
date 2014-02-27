import java.util.*;
import java.io.*;
import java.lang.*;
public class FileSearch extends Thread{
    private String fileToSearch;
    private String searchText;
    private List<String> result = new ArrayList<String>();
    public String getFileToSearch(){
            return fileToSearch;
    }

    public FileSearch(String fileToSearch,String  searchText){
            this.fileToSearch = fileToSearch;
            this.searchText = searchText;
    }

    public FileSearch(){
    }

    public List<String> getResult(){
            return result;
    }

    /*public void searchDirectory(File directory, String fileToSearch){
            //FileSearch(this.fileToSearch, this.searchText);
            FileSearch thread1 = new FileSearch(this.fileToSearch, this.searchText);
            if (directory.isDirectory()){
                    thread1.start();
                    try{
                            //search(directory);
                            thread1.join();
                    }catch(InterruptedException E){
                    }
            }//if
            else {
                    System.out.println(directory.getAbsoluteFile() + " is not a directory!");
            }//else

    }//searchDirectory*/

    public void search(File file)throws FileNotFoundException{
                 String text = "";
                 Scanner scan = new Scanner(file);
                 while(scan.hasNext()){
                         text += scan.nextLine();
                 }
                 int count = text.indexOf(this.searchText);
                 if(count >= 0){
                         //System.out.println("Results found at:" + file.getAbsoluteFile().toString());
                         //result.add();
                         print(file);
                 }
    }

    synchronized void print(File file){
        System.out.println("Results found at: " + file.getAbsoluteFile().toString());
    }
    public void run(){
            File file =  new File(this.fileToSearch);
                if (file.isDirectory()){
                    System.out.println("Searching directory: " + file.getAbsoluteFile());
                    List<FileSearch> threads = new ArrayList<FileSearch>();
                    for (File temp : file.listFiles()){
                            FileSearch thread1 = new FileSearch(temp.getName(), this.searchText);
                                thread1.start();
                                threads.add(thread1);
                    }//for
                    while(!threads.isEmpty()){
                        try{
                        threads.remove(0).join();
                        } catch(InterruptedException ie){ }
                    }
                }//if
                else{
                                try{
                                        search(file);
                                } catch(FileNotFoundException e){ }
                }
    }//if

    public static void main(String[] args){
        FileSearch fileSearch = new FileSearch();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the search criteria: ");
        String search = scan.next();
        System.out.println("Enter the path to search through: ");
        String path = scan.next();
        if(new File(path).exists()){
                //fileSearch.searchDirectory(new File(path), search);
                FileSearch newThread = new FileSearch(path, search);
                        newThread.start();
                        try{
                                newThread.join();
                        } catch(InterruptedException ie){}
                /*int count = newThread.getResult().size();
                if(count ==0){
                        System.out.println("No result found!");
                }//if
                else{
                        System.out.println("Found " + count + " result(s)!");
                        for (String matched : newThread.getResult()){
                                System.out.println("Found : " + matched);
                        }//for
              }//else*/
        }//if
        else{
                System.out.println("Path does not exist");
        }//else
    }//main
}
