import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetUserUsageRestrictionDetailComponent } from 'app/entities/dataset-user-usage-restriction/dataset-user-usage-restriction-detail.component';
import { DatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';

describe('Component Tests', () => {
  describe('DatasetUserUsageRestriction Management Detail Component', () => {
    let comp: DatasetUserUsageRestrictionDetailComponent;
    let fixture: ComponentFixture<DatasetUserUsageRestrictionDetailComponent>;
    const route = { data: of({ datasetUserUsageRestriction: new DatasetUserUsageRestriction(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetUserUsageRestrictionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetUserUsageRestrictionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetUserUsageRestrictionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetUserUsageRestriction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
