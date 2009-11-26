(ns info.reflectionsofmind.dicerobot.method.impl.d6)

(defn mapply [m k f] 
	(assoc m k (f (get m k))))  

(defstruct request :dice :pips)
(def dice (accessor request :dice)) 
(def pips (accessor request :pips)) 

(defn new-request [] 
	(struct-map request 
		:dice 0 
		:pips []))

(defn add-dice [request howMany] 
	(mapply request :dice (fn [value] (+ value howMany))))

(defn add-pips [request howMany] 
	(mapply request :pips (fn [list] (conj list howMany))))

(defn remove-from-end [n string] 
	(. string (substring 0 (- (count string) n))))

(def str-to-int #(. Integer (valueOf %)))

(defn modify-request [request token]  
	(if (. token (endsWith "d"))
		(add-dice request (str-to-int (remove-from-end 1 token)))
		(add-pips request (str-to-int token))))

(defn normalize [input]
	(.. input (toLowerCase) (replaceAll "\\s" "")))
 
(defn split [separator-regex input] (. input (split separator-regex)))
 
(defn parse [input] 
	(reduce modify-request (new-request) (split "\\+" (normalize input))))

(defstruct rolls :dice-rolls :wild-rolls)
(def dice-rolls (accessor rolls :dice-rolls)) 
(def wild-rolls (accessor rolls :wild-rolls))

(defn roll-expand [fn-roll-die]
	(let [roll (fn-roll-die)]
		(if (= roll 6) (conj (roll-expand fn-roll-die) 6) [roll])))

(defn roll-wild [fn-roll-die] 
	(#(if (= %1 1)
		[1 (fn-roll-die)] 
		(if (= %1 6) (roll-expand fn-roll-die)))) (fn-roll-die))

(defn roll [roller-factory request]
	(let [die-roller (. roller-factory (createDieRoller))]
		(defn fn-roll-die [] (. die-roller (roll 6)))
		(struct-map rolls
			:dice-rolls (map fn-roll-die (range (- (dice request) 1))) 
			:wild-rolls (roll-wild fn-roll-die)))) 
