const Nasabah = artifacts.require("NasabahContract");

module.exports = function(deployer) {
  deployer.deploy(Nasabah);
};
