import { module, test } from 'qunit';
import { setupTest } from 'lms-atmpt/tests/helpers';

module('Unit | Controller | readerdashboard', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let controller = this.owner.lookup('controller:readerdashboard');
    assert.ok(controller);
  });
});
