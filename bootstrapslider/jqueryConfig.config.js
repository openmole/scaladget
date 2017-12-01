module.exports = {
      "entry": {
    "demo-opt": [__dirname + "/bootstrapslider-opt.js"]
  },
  "output": {
    "path": __dirname,
    "filename": "[name]-bundle.js"
  },
    resolve: {
        alias: {
             "jquery": __dirname + "../../../resources/jquery-stub.js"
        }
    }
}