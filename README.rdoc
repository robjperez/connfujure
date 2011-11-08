Work in progress!!!

An example of application should be:

 (application TOKEN 
  (conference
     (voice 
     	(on-join [{dest :destination orig :origin}]
  		 (do 
      	  	     (println (str "new call receiverd on number " dest)
      	  	     (if (--> (find_by_conference_number dest) (is-allowed? orig))
              	    	(println "whitelist number received")
              	    	(println "not whitelist number")))))



Clojure version of Connfu ruby library
https://github.com/bluevialabs/connfu-client
