import requests
from flask import Flask
app = Flask(__name__)

@app.route("/")
def hello():
    return "Test World!"

@app.route("/home")
def home():
    return "Home World!" 

@app.route('/user/<user_id>')
def ping_cassandra(user_id):
    # pull from cassandra 
    r = requests.get("http://198.61.177.186:8080/virgil/data/glucoseapp/menu/%s" % user_id)
    return r.text      

if __name__ == "__main__":
    app.run()
