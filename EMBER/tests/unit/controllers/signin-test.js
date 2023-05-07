import { module, test } from 'qunit';
import { setupTest } from 'lms-atmpt/tests/helpers';

module('Unit | Controller | signin', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let controller = this.owner.lookup('controller:signin');
    assert.ok(controller);
  });
});
