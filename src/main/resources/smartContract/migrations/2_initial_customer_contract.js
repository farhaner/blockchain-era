const Customer = artifacts.require("CustomerContract");

module.exports = function(deployer) {
  deployer.deploy(Customer);
};
