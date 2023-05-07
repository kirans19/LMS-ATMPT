import { module, test } from 'qunit';
import { setupTest } from 'lms-atmpt/tests/helpers';

module('Unit | Service | UserService', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let service = this.owner.lookup('service:user-service');
    assert.ok(service);
  });
});
